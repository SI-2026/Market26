# Metodos nuevos

## Suscripcion
- [src/main/java/dataAccess/DataAccess.java](src/main/java/dataAccess/DataAccess.java): `buySubscription(String username)`, `cancelSubscription(String username)`, `addSystemFunds(float amount)`, `canRemoveSystemFunds(float amount)`, `removeSystemFunds(float amount)`
- [src/main/java/businessLogic/BLFacade.java](src/main/java/businessLogic/BLFacade.java): `buySubscription(String username)`, `cancelSubscription(String username)`
- [src/main/java/businessLogic/BLFacadeImplementation.java](src/main/java/businessLogic/BLFacadeImplementation.java): `buySubscription(String username)`, `cancelSubscription(String username)`
- [src/main/java/domain/Subscription.java](src/main/java/domain/Subscription.java): `Subscription()`, `Subscription(Date startDate)`, `getSubscriptionId()`, `getStartDate()`, `setStartDate(Date startDate)`, `isActive()`, `setActive(boolean active)`
- [src/main/java/domain/User.java](src/main/java/domain/User.java): `isSubscribed()`, `getSubscription()`, `setSubscription(Subscription subscription)`
- [src/main/java/configuration/UtilDate.java](src/main/java/configuration/UtilDate.java): `getDemoDate()`, `advanceDemoDay()`

## Demandas y ofertas
- [src/main/java/dataAccess/DataAccess.java](src/main/java/dataAccess/DataAccess.java): `createDemand(String username, String prod, String description)`, `getDemands(String search)`, `getDemand(int demandId)`, `addDemandOffer(int demandId, String sellerUsername, String product, String description, float price)`, `acceptDemandOffer(int demandId, int offerId, String ownerUsername)`, `declineDemandOffer(int demandId, int offerId, String ownerUsername)`
- [src/main/java/businessLogic/BLFacade.java](src/main/java/businessLogic/BLFacade.java): `createDemand(String username, String prod, String description)`, `getDemands(String search)`, `getDemand(int demandId)`, `addDemandOffer(int demandId, String sellerUsername, String product, String description, float price)`, `acceptDemandOffer(int demandId, int offerId, String ownerUsername)`, `declineDemandOffer(int demandId, int offerId, String ownerUsername)`
- [src/main/java/businessLogic/BLFacadeImplementation.java](src/main/java/businessLogic/BLFacadeImplementation.java): `createDemand(String username, String prod, String description)`, `getDemands(String search)`, `getDemand(int demandId)`, `addDemandOffer(int demandId, String sellerUsername, String product, String description, float price)`, `acceptDemandOffer(int demandId, int offerId, String ownerUsername)`, `declineDemandOffer(int demandId, int offerId, String ownerUsername)`
- [src/main/java/domain/Demand.java](src/main/java/domain/Demand.java): `Demand()`, `Demand(String username, String prod, String description)`, `getDemandId()`, `getUsername()`, `setUsername(String username)`, `getProd()`, `setProd(String prod)`, `getDescription()`, `setDescription(String description)`, `getPubDate()`, `isActive()`, `setActive(boolean active)`, `getOffers()`, `addOffer(DemandOffer offer)`, `removeOffer(DemandOffer offer)`
- [src/main/java/domain/DemandOffer.java](src/main/java/domain/DemandOffer.java): `DemandOffer()`, `DemandOffer(User seller, String product, String description, float price, Date offerDate)`, `getOfferId()`, `getSeller()`, `setSeller(User seller)`, `getProduct()`, `setProduct(String product)`, `getDescription()`, `setDescription(String description)`, `getPrice()`, `setPrice(float price)`, `getOfferDate()`, `setOfferDate(Date offerDate)`
- [src/main/java/domain/User.java](src/main/java/domain/User.java): `getDemands()`, `addDemand(String prod, String description)`, `makeEskaera(String username, String prod, String description)`
- [src/main/java/gui/DemandsGUI.java](src/main/java/gui/DemandsGUI.java): `DemandsGUI(String username)`
- [src/main/java/gui/QueryDemandsGUI.java](src/main/java/gui/QueryDemandsGUI.java): `QueryDemandsGUI(String username)`, `reloadDemands()`
- [src/main/java/gui/OfferDemandGUI.java](src/main/java/gui/OfferDemandGUI.java): `OfferDemandGUI(Demand demand, String username)`
- [src/main/java/gui/NireEskaerakGUI.java](src/main/java/gui/NireEskaerakGUI.java): `NireEskaerakGUI(String username)`, `loadDemands()`
- [src/main/java/gui/EskaeraOffersGUI.java](src/main/java/gui/EskaeraOffersGUI.java): `EskaeraOffersGUI(Demand demand, String username)`, `loadOffers()`
- [src/main/java/gui/ShowDemandGUI.java](src/main/java/gui/ShowDemandGUI.java): `ShowDemandGUI(Demand demand, String username)`, `buildOwnerSection()`, `buildOfferSection()`, `reloadDemand()`, `loadOffers()`

# Metodos existentes retocados

## Suscripcion y reclamaciones
- [src/main/java/dataAccess/DataAccess.java](src/main/java/dataAccess/DataAccess.java): `addOffer(float offer, int salenumber, String buyername)`, `acceptOffer(int offerId, int salenumber, String sellername)`, `makeClaim(String description, String sellername, String claimername)`, `canMakeClaim(User claimer, User seller, Date now)`
- [src/main/java/domain/User.java](src/main/java/domain/User.java): `addMovement(String nondik, String nora, float kopurua)`
- [src/main/java/gui/ProfileGUI.java](src/main/java/gui/ProfileGUI.java): `ProfileGUI(JFrame registeredRef, String username)`, `refreshBalance()`
- [src/main/java/gui/SellerOffersGUI.java](src/main/java/gui/SellerOffersGUI.java): `refresh()`
- [src/main/java/gui/MovementsGUI.java](src/main/java/gui/MovementsGUI.java): `MovementsGUI(String username)`
- [src/main/java/gui/ShowSaleGUI.java](src/main/java/gui/ShowSaleGUI.java): `ShowSaleGUI(Sale sale, String username)`

## Demandas y ofertas
- [src/main/java/gui/ProfileGUI.java](src/main/java/gui/ProfileGUI.java): `ProfileGUI(JFrame registeredRef, String username)`
- [src/main/java/gui/RegisteredGUI.java](src/main/java/gui/RegisteredGUI.java): `RegisteredGUI(User u)`, `paintAgain()`
- [src/main/java/gui/UserGUI.java](src/main/java/gui/UserGUI.java): `UserGUI()`, `paintAgain()`
