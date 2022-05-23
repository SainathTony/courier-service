# courier-service

A java app to track the package delivery

## Requirements

- Should take the input from user via console.
- Only one offer code should be applied for any given package.
- Package should meet the required below-mentioned offer criterias.
- If offer code is not applicable, discount should be 0.
- Each vehicle has a limit on maximum weight (kg) that it can carry.
- All Vehicles travel at the same speed (S km/hr) and in the same route. It is assumed that all the destinations can be
  covered in a single route.

### Offer Criteria

|Coupon Code| Discount | Distance | Weight    |
|-----------|----------|----------|-----------|
|OFR001|10%| < 200    | 70 - 200  |
|OFR002|7%| 50 - 150 | 100 - 150 |
|OFFER003|5%| 50 - 250 | 10 - 150  |


## Development Guidelines

1. The SOLID design principles
   http://en.wikipedia.org/wiki/SOLID_(object-oriented_design)
2. Use Functional programming (Lambda expressions, Streams, Optional, etc.) as much as possible.
3. Strictly follow Test Driven Development. http://butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd
4. Clean code http://blog.goyello.com/2013/01/21/top-9-principles-clean-code/

##Run Tests
```shell
    ./gradlew clean build   
```

##Start Application
```shell
    ./gradlew run
```

##Sample Input
```shell
  Enter base delivery cost and number of packages (space seperated): 100 5
  Enter Package_ID Package_Weight_In_KG Package_Delivery_Distance_In_KM Coupon_Code (Space seperated)
  Enter 1st package information: PKG1 50 30 OFR001
  Enter 2nd package information: PKG2 75 125 OFFR0008
  Enter 3rd package information: PKG3 175 100 OFFR003
  Enter 4th package information: PKG4 110 60 OFFR002
  Enter 5th package information: PKG5 155 95 NA
  Enter No_Of_Vehicles Max_Speed Max_Carry_Weight (space seperated): 2 70 200
```

##Sample Output
```shell
  ====== Delivery Summary ======
  PKG1   0.0   750   3.98
  PKG2   0.0   1475  1.78
  PKG3   0.0   2350  1.42
  PKG4   105.0 1395  0.85
  PKG5   0.0   2125  4.18
```