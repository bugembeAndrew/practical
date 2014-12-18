-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2014 at 09:58 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `current_prices`
--

-- --------------------------------------------------------

--
-- Table structure for table `commodity`
--

CREATE TABLE IF NOT EXISTS `commodity` (
  `commodity_id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_name` varchar(32) NOT NULL,
  PRIMARY KEY (`commodity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `commodity`
--

INSERT INTO `commodity` (`commodity_id`, `commodity_name`) VALUES
(1, 'Sweet potatoes'),
(2, 'Beans'),
(3, 'Ground nuts');

-- --------------------------------------------------------

--
-- Table structure for table `market`
--

CREATE TABLE IF NOT EXISTS `market` (
  `market_id` int(11) NOT NULL AUTO_INCREMENT,
  `market_name` varchar(32) NOT NULL,
  PRIMARY KEY (`market_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `market`
--

INSERT INTO `market` (`market_id`, `market_name`) VALUES
(1, 'St. Balikuddembe'),
(2, 'Kaleerwe'),
(3, 'Nakawa');

-- --------------------------------------------------------

--
-- Table structure for table `submissions`
--

CREATE TABLE IF NOT EXISTS `submissions` (
  `sub_id` int(11) NOT NULL AUTO_INCREMENT,
  `vendor_name` varchar(32) NOT NULL,
  `market_id` int(11) NOT NULL,
  `commodity_id` int(11) NOT NULL,
  `price` float(7,2) NOT NULL,
  `unit_id` int(11) NOT NULL,
  PRIMARY KEY (`sub_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `submissions`
--

INSERT INTO `submissions` (`sub_id`, `vendor_name`, `market_id`, `commodity_id`, `price`, `unit_id`) VALUES
(1, 'Bugembe Andrew', 3, 3, 2500.00, 3),
(2, 'Bugembe Andrew', 3, 2, 5000.00, 2),
(3, 'David Negam', 1, 3, 5000.00, 1),
(5, 'Bob Dylan', 3, 1, 1966.00, 2),
(6, 'Queen', 1, 3, 1986.00, 1),
(7, 'ABBA', 2, 2, 1970.00, 2),
(8, 'Bony M', 3, 1, 3000.00, 3),
(11, 'Daley Blind', 2, 1, 6650.00, 2),
(12, 'Cold Face', 1, 2, 4500.00, 1);

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE IF NOT EXISTS `unit` (
  `unit_id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(32) NOT NULL,
  PRIMARY KEY (`unit_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`unit_id`, `unit_name`) VALUES
(1, 'Kilo gramme'),
(2, 'Box'),
(3, 'Cup');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
