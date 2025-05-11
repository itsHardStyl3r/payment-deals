# Payment deals

This is a simple Java program that shows the best payment method for a given orders based on the provided payment methods and their discounts.

## Requirements

⚠️ You need Java 21 to run this program.

You need two files, one with the orders and the other one with payment methods.
Both of them are supposed to be json files, e.g.

- orders.json

```json
[
  {
    "id": "ORDER1",
    "value": "100.00",
    "promotions": [
      "mZysk"
    ]
  },
  {
    "id": "ORDER2",
    "value": "200.00",
    "promotions": [
      "BosBankrut"
    ]
  },
  {
    "id": "ORDER3",
    "value": "150.00",
    "promotions": [
      "mZysk",
      "BosBankrut"
    ]
  },
  {
    "id": "ORDER4",
    "value": "50.00"
  }
]
```

- paymentmethods.json

```json
[
  {
    "id": "PUNKTY",
    "discount": "15",
    "limit": "100.00"
  },
  {
    "id": "mZysk",
    "discount": "10",
    "limit": "180.00"
  },
  {
    "id": "BosBankrut",
    "discount": "5",
    "limit": "200.00"
  }
]
```

## Building

1. Clone the repository
2. Open the terminal and navigate to the project directory
3. Run the following command to build the project:
   ```bash
   mvn install
   ```
4. This will create a JAR file in the `target` directory.

## Running the program

You either need to build the program first, or download the JAR file from the releases. Run the following command to
execute the program:

```
java -jar payment-deals-1.0-SNAPSHOT.jar <path_to_orders.json> <path_to_paymentmethods.json>
```

## Testing

To run the tests, use the following command:

```bash
mvn test
```

and optionally, you can build a website with a raport and test results:

```bash
mvn site
```