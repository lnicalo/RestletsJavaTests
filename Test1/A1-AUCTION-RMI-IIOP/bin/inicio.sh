java -classpath . -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory -Djava.naming.provider.url=iiop://localhost:2000 a1.rmiiiop.auction.AuctionManager &
java -classpath . -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory -Djava.naming.provider.url=iiop://localhost:2000 a1.rmiiiop.item.ItemManager &