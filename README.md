# Wyzly Backend

This is the backend for the Wyzly application, a platform for restaurants to sell surplus food boxes and for customers to purchase them.

## Architecture

The backend follows a clean architecture approach with the following layers:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic
3. **Repository Layer**: Provides data access
4. **Model Layer**: Defines the domain entities
5. **DTO Layer**: Defines data transfer objects for API communication

## Features

- **Authentication**: Login, register, logout, get current user
- **Box Management**: Create, read, update, delete boxes
- **Order Management**: Create orders, view orders, update order status
- **Role-Based Access Control**: Customer, Restaurant, Admin roles

## API Endpoints

### Authentication

- `POST /api/auth/login`: Login with email and password
- `POST /api/auth/register`: Register a new user
- `POST /api/auth/logout`: Logout the current user
- `GET /api/auth/me`: Get the current user

### Boxes

- `GET /api/boxes`: Get all boxes
- `GET /api/boxes/{id}`: Get a box by ID
- `GET /api/boxes/restaurant/{restaurantId}`: Get boxes by restaurant ID
- `POST /api/boxes`: Create a new box
- `PUT /api/boxes/{id}`: Update a box
- `DELETE /api/boxes/{id}`: Delete a box
- `PATCH /api/boxes/{id}/quantity`: Update a box's quantity

### Orders

- `GET /api/orders`: Get all orders (admin only)
- `GET /api/orders/{id}`: Get an order by ID
- `GET /api/orders/user`: Get orders for the current user
- `GET /api/orders/user/with-box-details`: Get orders with box details for the current user
- `POST /api/orders`: Create a new order
- `PATCH /api/orders/{id}/status`: Update an order's status (admin and restaurant only)

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run `./gradlew bootRun` to start the application
4. The API will be available at `http://localhost:8080`
# wyzly-backend
