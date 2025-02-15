-- Διαγραφή πινάκων με σωστή σειρά για να μην υπάρχουν προβλήματα dependencies
DROP TABLE IF EXISTS `viewing_request`;
DROP TABLE IF EXISTS `rental_request`;
DROP TABLE IF EXISTS `property`;
DROP TABLE IF EXISTS `user`;

-- Δημιουργία πίνακα για τους χρήστες (User) - Χωρίς Role Table
CREATE TABLE `user` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL -- Χρησιμοποιούμε το Enum ως String
);

-- Δημιουργία πίνακα για τα ακίνητα (Property)
CREATE TABLE `property` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DOUBLE DEFAULT NULL,
    owner_id BIGINT(20) NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES `user`(id) ON DELETE CASCADE
);

-- Δημιουργία πίνακα για αιτήσεις ενοικίασης (RentalRequest)
CREATE TABLE `rental_request` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) DEFAULT NULL,
    request_date DATE NOT NULL,
    status ENUM('APPROVED','PENDING','REJECTED') NOT NULL DEFAULT 'PENDING',
    property_id BIGINT(20) NOT NULL,
    tenant_id BIGINT(20) NOT NULL,
    FOREIGN KEY (property_id) REFERENCES `property`(id) ON DELETE CASCADE,
    FOREIGN KEY (tenant_id) REFERENCES `user`(id) ON DELETE CASCADE
);

-- Δημιουργία πίνακα για αιτήσεις προβολής (ViewingRequest)
CREATE TABLE `viewing_request` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(500) DEFAULT NULL,
    requested_date_time DATETIME(6) NOT NULL,
    status ENUM('APPROVED','PENDING','REJECTED') NOT NULL DEFAULT 'PENDING',
    property_id BIGINT(20) NOT NULL,
    tenant_id BIGINT(20) NOT NULL,
    FOREIGN KEY (property_id) REFERENCES `property`(id) ON DELETE CASCADE,
    FOREIGN KEY (tenant_id) REFERENCES `user`(id) ON DELETE CASCADE
);

-- Εισαγωγή δεδομένων στον πίνακα `user`
INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES
(88, 'admin', '$2a$10$9eThjtoNvWOxStyv3hZY4.X5sA4Of2mOJh0zsCMJ.89csE3XO9YzS', 'ADMIN'),
(89, 'owner', '$2a$10$2.IFE08H0TGIlr61QrkmUu1V5rszs/0ycG7iO0EXwE0QzCxoa8hIK', 'OWNER'),
(90, 'tenant', '$2a$10$O0u3rDxRUtPjuOpnnRcEEOWr0nK8wKMHzngQQ2bDrhIjJ8h1aXvA.', 'TENANT');

-- Εισαγωγή δεδομένων στον πίνακα `property`
INSERT INTO `property` (`id`, `location`, `name`, `price`, `owner_id`) VALUES
(59, 'Athens', 'Luxury Apartment', 1500, 89),
(60, 'Thessaloniki', 'Cozy House', 1200, 89);

-- Εισαγωγή δεδομένων στον πίνακα `rental_request`
INSERT INTO `rental_request` (`id`, `message`, `request_date`, `status`, `property_id`, `tenant_id`) VALUES
(30, 'I would like to rent this property.', '2025-02-15', 'PENDING', 59, 90);

-- Εισαγωγή δεδομένων στον πίνακα `viewing_request`
INSERT INTO `viewing_request` (`id`, `message`, `requested_date_time`, `status`, `property_id`, `tenant_id`) VALUES
(30, 'I would like to schedule a viewing.', '2025-02-15 13:18:12.000000', 'PENDING', 60, 90);

-- AUTO_INCREMENT για πίνακα `property`
ALTER TABLE `property`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

-- AUTO_INCREMENT για πίνακα `rental_request`
ALTER TABLE `rental_request`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

-- AUTO_INCREMENT για πίνακα `user`
ALTER TABLE `user`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

-- AUTO_INCREMENT για πίνακα `viewing_request`
ALTER TABLE `viewing_request`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

COMMIT;