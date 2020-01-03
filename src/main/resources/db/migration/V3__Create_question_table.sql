create table Question
(
	ID int auto_increment primary key ,
	title varchar(50),
	description text,
	creator_id int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	gmt_created bigint,
	gmt_modified bigint
);
