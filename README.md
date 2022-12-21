# Bridge: Java Redis PubSub Wrapper

## Add Bridge to your own build
### Maven
```xml
<repository>
  <id>bridge</id>
  <url>https://raw.github.com/AcaiSoftware/bridge/repository/</url>
</repository>

<dependency>
  <groupId>gg.acai</groupId>
  <artifactId>bridge</artifactId>
  <version>3.0.1</version>
</dependency>
```

## Usage Examples
### Registering Bridge with a Configuration
All builder fields are optional except for ``redisServer``.
```java
BridgeConfiguration config = BridgeConfiguration.newBuilder()
    .threads(3) // the amount of threads to run bridge on
    .debug(true) // whether to enable debug mode
    .upperCasePacketIdentifiers(false) // whether to uppercase packet identifiers. (e.g. "test" -> "TEST") for @Identifier
    .redisClient(RedisComponent.newBuilder() // required field: redis server
        .host("localhost")
        .port(6379)
        .password("password")
        .addAgent(new Agent("bridge")) // required field: redis pubsub agent with its channel identifier
        .addAgent(new Agent("channel", new BridgeMessenger())) // add custom messenger with its channel identifier
        .lock(true) // whether to use locks upon redis operations
        .build()) // build the redis server
    .build(); // build the configuration
    
Bridge bridge = new Bridge(config); // setup bridge with the configuration
```

### Creating & Registering Packet Listener
```java
@Identifier("mypacket") @Channel("bridge") // identifier and channel target
public class MyPacket implements PacketListener {
    @Override
    public void onIncomingPacket(BridgePacket packet) {
        System.out.println("Received packet: " + packet.getParameter("key")); // retrieve the parameter "key", which is "hello"
        Object o = packet.getSerializedParameter("serializedObject", Object.class); // deserialize the object
    }
}
```
```java
RequestMapper registry = bridge.getRegistry();
registry.register(new MyPacket()); // register the packet
```

### Sending a Packet
```java
bridge.publish("channel", Packets.createPacket("mypacket") // the channel target & identifier of the packet
    .withRawParameter("key", "hello") // key, value
    .withSerializableParameter("serializedObject", new Object(), Object.class) // a serializable object, using gson to serialize
    .toJson()); // convert the request to json
```

### Packets With Callbacks
#### Sending a callback packet
```java
UUID uuid = UUID.randomUUID(); // acts as a User ID
bridge.publish("channel", Packets.createPacket("user::lookup") // the identifier of the packet
    .withRawParameter("user", uuid.toString())
    .toJson()); // convert the request to json
```

#### Create a Callback Packet Listener
```java
@Identifier("user::lookup")
public class LookUpPacket implements PacketListener {
    @Override
    public void onIncomingPacket(BridgePacket packet) {
        UUID uuid = UUID.fromString(packet.getParameter("user")); // convert the user parameter to a UUID
        User user = network.getUser(uuid); // example object
        boolean successful = user != null && user.isOnline(); // assume this is a valid check
        
        bridge.publish(Packets.createCallbackPacket() // create a callback packet
            .withRawParameter("user", uuid.toString())
            .withRawParameter("successful", String.valueOf(successful)) // required field: successful
            .withRawParameter("identifier", "user::lookup") // highly recommended, yet optional field: identifier
            .toJson());
    }
}
```

#### Creating a Callback Listener
```java
@CallbackIdentifier("user::lookup") // the identifier of the callback
public class LookupCallback implements Callback {
    
    @Override
    public void onCallback(BridgePacket request, boolean success) {
        System.out.println("Received lookup callback");
        System.out.println("User Online: " + success);
    }

    @Override
    public boolean isAwaitingRequest() {
        return true;
    }
}
```
#### Registering a Callback Listener
```java
bridge.getCallbackChain().subscribe(new LookupCallback());
```

### Disconnecting from Bridge
```java
bridge.close();
```


## Documentation
Coming soon

## Contributing
Contributions are highly appreciated! If you feel your pull request is useful, go ahead!
Before creating a pull request, make sure your changes works as it should and give a description on what it provides.