-- Διαγραφή πινάκων με σωστή σειρά για να μην υπάρχουν προβλήματα dependencies
DROP TABLE IF EXISTS `ViewingRequest`;
DROP TABLE IF EXISTS `RentalRequest`;
DROP TABLE IF EXISTS `Property`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Role`;

-- Δημιουργία πίνακα για τους ρόλους (Role)
CREATE TABLE `Role` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Δημιουργία πίνακα για τους χρήστες (User)
CREATE TABLE `User` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES `Role`(id)
);

-- Δημιουργία πίνακα για τα ακίνητα (Property)
CREATE TABLE `Property` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    price DECIMAL(10, 2),
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES `User`(id)
);

-- Δημιουργία πίνακα για αιτήσεις ενοικίασης (RentalRequest)
CREATE TABLE `RentalRequest` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tenant_id INT,
    property_id INT,
    status VARCHAR(50) DEFAULT 'Pending',
    request_date DATE,
    FOREIGN KEY (tenant_id) REFERENCES `User`(id),
    FOREIGN KEY (property_id) REFERENCES `Property`(id)
);

-- Δημιουργία πίνακα για αιτήσεις προβολής (ViewingRequest)
CREATE TABLE `ViewingRequest` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tenant_id INT,
    property_id INT,
    viewing_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (tenant_id) REFERENCES `User`(id),
    FOREIGN KEY (property_id) REFERENCES `Property`(id)
);
