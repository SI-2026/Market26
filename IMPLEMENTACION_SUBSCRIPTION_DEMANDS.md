# Implementacion de suscripciones y sistema de demandas

## Suscripciones

- En `User` existe el campo `subscription` y se controla el estado con `isSubscribed()`.
- En `DataAccess` hay constantes para precio y reglas:
  - `SUBSCRIPTION_PRICE` para el coste.
  - `CASHBACK_RATE` para devolver una parte en compras.
  - `SUBSCRIPTION_CANCEL_DAYS` para el plazo de cancelacion.
- Flujo de compra:
  - `buySubscription(username)` crea o reactiva `Subscription` y cobra el importe.
  - Se registra un movimiento hacia `SYSTEM_FUNDS`.
- Flujo de cancelacion:
  - `cancelSubscription(username)` comprueba el plazo y devuelve el importe si procede.
- Efectos de la suscripcion:
  - En compras se aplica cashback cuando hay suscripcion activa.
  - En reclamaciones, el plazo pasa a 30 dias (sin suscripcion son 7 dias).
- Para la demo se usa una fecha de simulacion:
  - `UtilDate.getDemoDate()` devuelve la fecha actual de demo.
  - `UtilDate.advanceDemoDay()` avanza un dia.
  - Se agrego un boton en el menu principal para avanzar dias.

## Demandas y ofertas

### Modelo de dominio

- `Demand` representa una demanda creada por un usuario:
  - `demandId`, `username`, `prod`, `description`, `pubDate`, `active`.
  - Lista de ofertas `offers`.
- `DemandOffer` representa una oferta sobre una demanda:
  - `seller`, `product`, `description`, `price`, `offerDate`.
- `User` contiene la lista de demandas propias (eskaerak).

### GUI global (demands)

- `QueryDemandsGUI` muestra la lista global.
- Doble click sobre una demanda abre `OfferDemandGUI`.
- `OfferDemandGUI` permite crear una oferta (producto, descripcion, precio) y llama a:
  - `BLFacade.addDemandOffer(demandId, sellerUsername, product, description, price)`.

### GUI de demandas propias

- En `ProfileGUI` se agrega el boton **Nire Eskaerak**.
- `NireEskaerakGUI` muestra la lista de demandas propias.
- Doble click abre `EskaeraOffersGUI` para ver las ofertas de esa demanda.
- `EskaeraOffersGUI` permite:
  - **Onartu**: acepta la oferta seleccionada.
  - **Ezeztatu**: rechaza la oferta seleccionada.
  - Los botones solo se activan si hay una oferta seleccionada.

### Logica de negocio

- `DataAccess.addDemandOffer(...)` valida datos y registra la oferta.
- `DataAccess.acceptDemandOffer(...)`:
  - Comprueba propietario y fondos.
  - Cobra importe + tasa, registra movimientos.
  - Aplica cashback si hay suscripcion.
  - Marca demanda como inactiva y borra ofertas.
  - Elimina la demanda de la lista del usuario.
- `DataAccess.declineDemandOffer(...)` elimina la oferta seleccionada.

## Reclamaciones

- `makeClaim(...)` valida el plazo con `canMakeClaim`.
- `canMakeClaim` ahora permite reclamar si hay:
  - Compra registrada en `Purchase`, o
  - Movimiento de dinero del comprador al vendedor.
- El plazo es:
  - 7 dias sin suscripcion.
  - 30 dias con suscripcion.

## Movimientos y reclamacion desde MovementsGUI

- `MovementsGUI` lista los movimientos del usuario.
- Doble click en un movimiento de pago (del usuario a otro vendedor) abre el prompt de reclamacion.
- Se reutilizan los textos de `ShowSaleGUI` para el flujo de reclamacion.
- `User.addMovement(...)` usa la fecha de demo para que el avance de dias afecte a las reclamaciones.
