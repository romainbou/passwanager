
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `passwanager`
--
CREATE DATABASE IF NOT EXISTS `passwanager` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `passwanager`;

-- --------------------------------------------------------

--
-- Table structure for table `entry`
--

CREATE TABLE `entry` (
  `id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `title` varchar(512) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `url` text,
  `notes` text,
  `ref_user` int(11) NOT NULL,
  `ref_folder` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entry`
--

INSERT INTO `entry` (`id`, `created_at`, `title`, `username`, `url`, `notes`, `ref_user`, `ref_folder`) VALUES
(9, '2016-03-01 13:44:15', 'Database access', 'admin', 'http://database.com', 'MySQL database', 8, 29),
(10, '2016-03-01 13:44:49', 'FTP access', 'admin', 'ftp://passwanager.com', '', 8, 29);

-- --------------------------------------------------------

--
-- Table structure for table `folder`
--

CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `name` varchar(512) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `ref_owner` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `folder`
--

INSERT INTO `folder` (`id`, `name`, `created_at`, `ref_owner`) VALUES
(29, 'Secrets', '2016-03-01 13:41:37', 8);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(512) DEFAULT NULL,
  `firstname` varchar(512) DEFAULT NULL,
  `lastname` varchar(512) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  `password` varchar(512) DEFAULT NULL,
  `public_key` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `firstname`, `lastname`, `username`, `password`, `public_key`) VALUES
(8, 'john@doe.com', 'John', 'Doe', 'john', '$2a$10$.teQM3gcGBZ/y/2uNEjeZOr2JOer8xgSReDMb.2RXIgnwGMMAXD/W', '-----BEGIN RSA PUBLIC KEY-----\nMIIBCgKCAQEAgjIaqQQ4pryWuL6F/l3xC+phtbEdPg5aU0EDl6o0SZOMX61gzSeVuvvGFNVi\naBpftfGtp0/sge2GM3+hBs2qSqbsDf/Anw+2EiCLOu+7b0+jHpCiWQG6JzP4/oTK4MnvZq1j\nxWM8TpyUVHwQHjLej0MTSZndUFWVAnZEFWGkSLeW6ws1Kl2SdrFbc8KoDKf/sNtzWES4CvFU\nHjs5oSwWOZXYIjvKH8ZUqS5d+i8fj8pke8E4aIGuRb1yiBlYedQopPqgLaH0i3y5Z58+LCA8\noCFSioZUQ9fr6bGSPQLV4k1/XykvjEcsSoMOb7htTrm5mFhyc6lZ49c5voNn+PlxdQIDAQAB\n-----END RSA PUBLIC KEY-----\n\n');

-- --------------------------------------------------------

--
-- Table structure for table `user_folder_link`
--

CREATE TABLE `user_folder_link` (
  `user_id` int(11) NOT NULL,
  `folder_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_folder_link`
--

INSERT INTO `user_folder_link` (`user_id`, `folder_id`) VALUES
(8, 29);

-- --------------------------------------------------------

--
-- Table structure for table `value`
--

CREATE TABLE `value` (
  `id` int(11) NOT NULL,
  `value` text NOT NULL,
  `ref_user` int(11) NOT NULL,
  `ref_entry` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `entry`
--
ALTER TABLE `entry`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_entity_user1_idx` (`ref_user`),
  ADD KEY `fk_entity_folder1_idx` (`ref_folder`);

--
-- Indexes for table `folder`
--
ALTER TABLE `folder`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_folder_user1_idx` (`ref_owner`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `user_folder_link`
--
ALTER TABLE `user_folder_link`
  ADD PRIMARY KEY (`user_id`,`folder_id`),
  ADD KEY `fk_user_has_folder_folder1_idx` (`folder_id`),
  ADD KEY `fk_user_has_folder_user_idx` (`user_id`);

--
-- Indexes for table `value`
--
ALTER TABLE `value`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_value_user1_idx` (`ref_user`),
  ADD KEY `fk_value_entity1_idx` (`ref_entry`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `entry`
--
ALTER TABLE `entry`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `folder`
--
ALTER TABLE `folder`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `value`
--
ALTER TABLE `value`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `entry`
--
ALTER TABLE `entry`
  ADD CONSTRAINT `fk_entity_folder1` FOREIGN KEY (`ref_folder`) REFERENCES `folder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entity_user1` FOREIGN KEY (`ref_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `folder`
--
ALTER TABLE `folder`
  ADD CONSTRAINT `fk_folder_user1` FOREIGN KEY (`ref_owner`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `user_folder_link`
--
ALTER TABLE `user_folder_link`
  ADD CONSTRAINT `fk_user_has_folder_folder1` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_folder_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `value`
--
ALTER TABLE `value`
  ADD CONSTRAINT `fk_value_entity1` FOREIGN KEY (`ref_entry`) REFERENCES `entry` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_value_user1` FOREIGN KEY (`ref_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
