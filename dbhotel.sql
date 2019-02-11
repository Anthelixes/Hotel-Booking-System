-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Gazdă: 127.0.0.1
-- Timp de generare: feb. 11, 2019 la 04:37 PM
-- Versiune server: 10.1.36-MariaDB
-- Versiune PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Bază de date: `dbhotel`
--

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `beneficii`
--

CREATE TABLE `beneficii` (
  `beneficiuID` int(11) NOT NULL,
  `descriere` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `beneficii`
--

INSERT INTO `beneficii` (`beneficiuID`, `descriere`) VALUES
(1, 'room service'),
(2, 'spalatorie'),
(3, 'fax/copiator'),
(4, 'parcare');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `beneficiicamera`
--

CREATE TABLE `beneficiicamera` (
  `cameraID` int(11) NOT NULL,
  `beneficiuID` int(11) NOT NULL,
  `cost` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `beneficiicamera`
--

INSERT INTO `beneficiicamera` (`cameraID`, `beneficiuID`, `cost`) VALUES
(1, 1, 40),
(1, 2, 40),
(1, 4, 10),
(2, 3, 10),
(3, 4, 20);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `camera`
--

CREATE TABLE `camera` (
  `cameraID` int(11) NOT NULL,
  `tipID` int(11) DEFAULT NULL,
  `pret` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `camera`
--

INSERT INTO `camera` (`cameraID`, `tipID`, `pret`) VALUES
(1, 1, 100),
(2, 1, 100),
(3, 2, 200),
(4, 1, 120);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `client`
--

CREATE TABLE `client` (
  `clientID` int(11) NOT NULL,
  `nume` varchar(50) NOT NULL,
  `prenume` varchar(50) NOT NULL,
  `CNP` char(13) NOT NULL,
  `telefon` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `client`
--

INSERT INTO `client` (`clientID`, `nume`, `prenume`, `CNP`, `telefon`) VALUES
(1, 'Popescu', 'Ion', '1234567890123', '0700000000'),
(2, 'Popescu', 'Cornelia', '2134567890123', '0710000000'),
(3, 'Enciu', 'Elena', '2091128701231', '0700200302'),
(4, 'Apetroaie', 'Claudiu', '1970116012312', '0700100101'),
(5, 'Apetroaie', 'Razvan', '1980222012312', '0700100101'),
(6, 'Tudose', 'Viorica', '2013105423121', '0700100102'),
(7, 'Tudose', 'Viorel', '1013105423121', '0700100102');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `factura`
--

CREATE TABLE `factura` (
  `facturaID` int(11) NOT NULL,
  `cameraID` int(11) DEFAULT NULL,
  `clientID` int(11) DEFAULT NULL,
  `checkIn` datetime DEFAULT NULL,
  `checkOut` datetime DEFAULT NULL,
  `nrNopti` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `factura`
--

INSERT INTO `factura` (`facturaID`, `cameraID`, `clientID`, `checkIn`, `checkOut`, `nrNopti`) VALUES
(1, 1, 1, '2018-12-03 00:00:00', '2018-12-04 00:00:00', 1),
(2, 2, 2, '2018-12-18 00:00:00', '2018-12-24 00:00:00', 5),
(3, 3, 1, '2019-02-01 00:00:00', '2019-02-02 00:00:00', 1),
(4, 4, 2, '2019-01-15 00:00:00', '2019-01-18 00:00:00', 3),
(5, 1, 2, '2019-02-08 00:00:00', '2019-02-11 00:00:00', 3),
(6, 2, 2, '2019-02-07 00:00:00', '2019-02-09 00:00:00', 2),
(15, 1, 5, '2019-02-22 00:00:00', '2019-02-23 00:00:00', 1);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `facturabeneficii`
--

CREATE TABLE `facturabeneficii` (
  `facturaID` int(11) NOT NULL,
  `beneficiuID` int(11) NOT NULL,
  `Info` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `facturabeneficii`
--

INSERT INTO `facturabeneficii` (`facturaID`, `beneficiuID`, `Info`) VALUES
(1, 4, NULL),
(3, 4, NULL),
(15, 1, NULL),
(15, 2, NULL),
(15, 4, NULL);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `tipcamera`
--

CREATE TABLE `tipcamera` (
  `tipID` int(11) NOT NULL,
  `denumire` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `tipcamera`
--

INSERT INTO `tipcamera` (`tipID`, `denumire`) VALUES
(1, 'Single'),
(2, 'Twin');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'admin', '1234');

--
-- Indexuri pentru tabele eliminate
--

--
-- Indexuri pentru tabele `beneficii`
--
ALTER TABLE `beneficii`
  ADD PRIMARY KEY (`beneficiuID`);

--
-- Indexuri pentru tabele `beneficiicamera`
--
ALTER TABLE `beneficiicamera`
  ADD PRIMARY KEY (`cameraID`,`beneficiuID`),
  ADD KEY `beneficiuID` (`beneficiuID`);

--
-- Indexuri pentru tabele `camera`
--
ALTER TABLE `camera`
  ADD PRIMARY KEY (`cameraID`),
  ADD KEY `tipID` (`tipID`);

--
-- Indexuri pentru tabele `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`clientID`),
  ADD UNIQUE KEY `CNP` (`CNP`);

--
-- Indexuri pentru tabele `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`facturaID`),
  ADD KEY `cameraID` (`cameraID`),
  ADD KEY `clientID` (`clientID`);

--
-- Indexuri pentru tabele `facturabeneficii`
--
ALTER TABLE `facturabeneficii`
  ADD PRIMARY KEY (`facturaID`,`beneficiuID`),
  ADD KEY `beneficiuID` (`beneficiuID`);

--
-- Indexuri pentru tabele `tipcamera`
--
ALTER TABLE `tipcamera`
  ADD PRIMARY KEY (`tipID`);

--
-- Indexuri pentru tabele `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pentru tabele eliminate
--

--
-- AUTO_INCREMENT pentru tabele `beneficii`
--
ALTER TABLE `beneficii`
  MODIFY `beneficiuID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pentru tabele `camera`
--
ALTER TABLE `camera`
  MODIFY `cameraID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pentru tabele `client`
--
ALTER TABLE `client`
  MODIFY `clientID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pentru tabele `factura`
--
ALTER TABLE `factura`
  MODIFY `facturaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pentru tabele `tipcamera`
--
ALTER TABLE `tipcamera`
  MODIFY `tipID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pentru tabele `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constrângeri pentru tabele eliminate
--

--
-- Constrângeri pentru tabele `beneficiicamera`
--
ALTER TABLE `beneficiicamera`
  ADD CONSTRAINT `beneficiicamera_ibfk_1` FOREIGN KEY (`cameraID`) REFERENCES `camera` (`cameraID`),
  ADD CONSTRAINT `beneficiicamera_ibfk_2` FOREIGN KEY (`beneficiuID`) REFERENCES `beneficii` (`beneficiuID`);

--
-- Constrângeri pentru tabele `camera`
--
ALTER TABLE `camera`
  ADD CONSTRAINT `camera_ibfk_1` FOREIGN KEY (`tipID`) REFERENCES `tipcamera` (`tipID`);

--
-- Constrângeri pentru tabele `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`cameraID`) REFERENCES `camera` (`cameraID`),
  ADD CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`);

--
-- Constrângeri pentru tabele `facturabeneficii`
--
ALTER TABLE `facturabeneficii`
  ADD CONSTRAINT `facturabeneficii_ibfk_1` FOREIGN KEY (`beneficiuID`) REFERENCES `beneficii` (`beneficiuID`),
  ADD CONSTRAINT `facturabeneficii_ibfk_2` FOREIGN KEY (`facturaID`) REFERENCES `factura` (`facturaID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
