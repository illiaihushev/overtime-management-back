CREATE TABLE positions (
	id int2 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT positions_pk PRIMARY KEY (id)
);

CREATE TABLE actions (
	id int2 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT actions_pk PRIMARY KEY (id)
);


CREATE TABLE object_types (
	id int2 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT object_types_pk PRIMARY KEY (id)
);


CREATE TABLE permissions (
	id int2 NOT NULL,
	action_id int2 NOT NULL,
	object_type_id int2 NOT NULL,
	CONSTRAINT permissions_pk PRIMARY KEY (id),
	CONSTRAINT permissions_actions_fk FOREIGN KEY (action_id) REFERENCES actions(id),
	CONSTRAINT permissions_object_types_fk FOREIGN KEY (object_type_id) REFERENCES object_types(id)
);


CREATE TABLE roles (
	id int2 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
);


CREATE TABLE scopes (
	id int2 NOT NULL,
	for_subordinate bool NOT NULL,
	for_yourself bool NOT NULL,
	CONSTRAINT scopes_pk PRIMARY KEY (id)
);


CREATE TABLE roles_permissions (
	role_id int2 NOT NULL,
	permission_id int2 NOT NULL,
	scope_id int2 NOT NULL,
	CONSTRAINT roles_permissions_pk PRIMARY KEY (role_id, permission_id),
	CONSTRAINT roles_permissions_permissions_fk FOREIGN KEY (permission_id) REFERENCES permissions(id),
	CONSTRAINT roles_permissions_roles_fk FOREIGN KEY (role_id) REFERENCES roles(id),
	CONSTRAINT roles_permissions_scopes_fk FOREIGN KEY (scope_id) REFERENCES scopes(id)
);


CREATE TABLE positions_roles (
	position_id int2 NOT NULL,
	role_id int2 NOT NULL,
	priority int2 NOT NULL,
	CONSTRAINT positions_roles_pk PRIMARY KEY (position_id, role_id),
	CONSTRAINT positions_roles_positions_fk FOREIGN KEY (position_id) REFERENCES positions(id),
	CONSTRAINT positions_roles_roles_fk FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE users (
	id bigserial NOT NULL,
	position_id int2 NOT NULL,
	manager_id int8 NULL,
	"name" varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	last_update timestamp NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_manager_fk FOREIGN KEY (manager_id) REFERENCES users(id),
	CONSTRAINT users_positions_fk FOREIGN KEY (position_id) REFERENCES positions(id)
);

CREATE TABLE overtimes (
	id bigserial NOT NULL,
	employee_id int8 NOT NULL,
	creator_id int8 NOT NULL,
	"date" date NOT NULL,
	duration int2 NOT NULL,
	"comment" varchar(400) NOT NULL,
	dms_number varchar(255) NOT NULL,
	dms_days int4 NOT NULL,
	status varchar(255) NOT NULL,
	rejected_comment varchar(400) NULL,
	CONSTRAINT overtimes_pk PRIMARY KEY (id),
	CONSTRAINT overtimes_users_creator_fk FOREIGN KEY (creator_id) REFERENCES users(id),
	CONSTRAINT overtimes_users_employee_fk FOREIGN KEY (employee_id) REFERENCES users(id)
);


CREATE TABLE attach_types (
	id int2 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT attach_types_pk PRIMARY KEY (id)
);

CREATE TABLE attaches (
	id smallserial NOT NULL,
	attach_type_id int2 NOT NULL,
	"content" bytea NOT NULL,
	overtime_id int8 NOT NULL,
	CONSTRAINT attaches_pk PRIMARY KEY (id),
	CONSTRAINT attaches_attach_types_fk FOREIGN KEY (attach_type_id) REFERENCES attach_types(id),
	CONSTRAINT attaches_overtimes_fk FOREIGN KEY (overtime_id) REFERENCES overtimes(id)
);

CREATE TABLE projects (
	id bigserial NOT NULL,
	pmo_id int8 NOT NULL,
	"name" varchar(255) NOT NULL,
	last_update timestamp NULL,
	CONSTRAINT projects_pk PRIMARY KEY (id),
	CONSTRAINT projects_users_fk FOREIGN KEY (pmo_id) REFERENCES users(id)
);

CREATE TABLE projects_users (
	project_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT projects_users_pk PRIMARY KEY (project_id, user_id),
	CONSTRAINT projects_users_projects_fk FOREIGN KEY (project_id) REFERENCES projects(id),
	CONSTRAINT projects_users_users_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE vacations (
	id bigserial NOT NULL,
	user_id int8 NOT NULL,
	start_date date NOT NULL,
	end_date date NOT NULL,
	CONSTRAINT vacations_pk PRIMARY KEY (id),
	CONSTRAINT vacations_users_fk FOREIGN KEY (user_id) REFERENCES users(id)
);








