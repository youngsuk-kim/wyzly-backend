-- Mock data for Wyzly application

-- Users (roles: USER, ADMIN)
INSERT INTO users (email, password, name, role, createdAt, updatedAt)
VALUES 
('user1@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'John Doe', 'RESTAURANT', NOW(), NOW()),
('user2@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Jane Smith', 'CUSTOMER', NOW(), NOW()),
('admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Admin User', 'ADMIN', NOW(), NOW());

-- Boxes
INSERT INTO boxes (title, price, quantity, image, restaurantId, createdAt, updatedAt)
VALUES 
('Lunch Box A', 12.99, 50, 'https://www.foodiesfeed.com/wp-content/uploads/2023/09/fresh-vegetables.jpg.webp', 'rest-001', NOW(), NOW()),
('Dinner Box B', 15.99, 30, 'https://images.unsplash.com/photo-1511690656952-34342bb7c2f2?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8Zm9vZHxlbnwwfHwwfHx8MA%3D%3D', 'rest-001', NOW(), NOW()),
('Special Box C', 19.99, 20, 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Zm9vZHxlbnwwfHwwfHx8MA%3D%3D', 'rest-002', NOW(), NOW()),
('Vegan Box D', 14.99, 25, 'https://plus.unsplash.com/premium_photo-1675252369719-dd52bc69c3df?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8Zm9vZHxlbnwwfHwwfHx8MA%3D%3D', 'rest-002', NOW(), NOW()),
('Premium Box E', 24.99, 15, 'https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fGZvb2R8ZW58MHx8MHx8fDA%3D', 'rest-003', NOW(), NOW());

-- Orders (statuses: PENDING, CONFIRMED, COMPLETED, CANCELLED)
INSERT INTO orders (boxId, customerId, quantity, totalPrice, status, createdAt, updatedAt)
VALUES 
(1, 1, 2, 25.98, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY)),
(2, 1, 1, 15.99, 'CONFIRMED', DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
(3, 1, 1, 19.99, 'PENDING', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(1, 2, 3, 38.97, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 45 DAY), DATE_SUB(NOW(), INTERVAL 43 DAY)),
(4, 2, 2, 29.98, 'CANCELLED', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(5, 2, 1, 24.99, 'PENDING', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

-- Note: The password hash is for 'password123' using BCrypt
