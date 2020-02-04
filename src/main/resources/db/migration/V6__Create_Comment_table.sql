create table Comment
(
	id bigint auto_increment,
	question_id int not null,
	commentor_id int not null,
	content varchar(1024),
	like_count int,
	gmt_modified bigint,
	gmt_created bigint
);

create unique index Comment_id_uindex
	on Comment (id);

alter table Comment
	add constraint Comment_pk
		primary key (id);

