CREATE TABLE IF NOT EXISTS courses(
    course_id INT AUTO_INCREMENT NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    course_category VARCHAR(20) NOT NULL,
    PRIMARY KEY (course_id)
);