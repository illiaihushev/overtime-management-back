INSERT INTO actions
(id, "name")
VALUES(1, 'create');
INSERT INTO actions
(id, "name")
VALUES(2, 'edit');
INSERT INTO actions
(id, "name")
VALUES(3, 'watch');
INSERT INTO actions
(id, "name")
VALUES(4, 'partial_edit');

INSERT INTO object_types
(id, "name")
VALUES(1, 'generic_info');
INSERT INTO object_types
(id, "name")
VALUES(2, 'dms_info');
INSERT INTO object_types
(id, "name")
VALUES(3, 'status');

INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(1, 1, 1);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(2, 2, 1);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(3, 3, 1);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(4, 1, 2);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(5, 2, 2);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(6, 3, 2);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(7, 1, 3);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(8, 2, 3);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(9, 3, 3);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(10, 4, 1);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(11, 4, 2);
INSERT INTO permissions
(id, action_id, object_type_id)
VALUES(12, 4, 3);

INSERT INTO roles
(id, "name")
VALUES(1, 'employee');
INSERT INTO roles
(id, "name")
VALUES(2, 'manager');
INSERT INTO roles
(id, "name")
VALUES(3, 'high-level manager');

INSERT INTO positions
(id, "name")
VALUES(1, 'employee');
INSERT INTO positions
(id, "name")
VALUES(2, 'manager');
INSERT INTO positions
(id, "name")
VALUES(3, 'PMO');
INSERT INTO positions
(id, "name")
VALUES(4, 'LM');

INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(1, 1, 1);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(2, 2, 1);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(2, 1, 2);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(3, 3, 1);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(3, 2, 2);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(3, 1, 3);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(4, 3, 1);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(4, 2, 2);
INSERT INTO positions_roles
(position_id, role_id, priority)
VALUES(4, 1, 3);

INSERT INTO scopes
(id, for_subordinate, for_yourself)
VALUES(1, true, true);
INSERT INTO scopes
(id, for_subordinate, for_yourself)
VALUES(2, false, true);
INSERT INTO scopes
(id, for_subordinate, for_yourself)
VALUES(3, true, false);
INSERT INTO scopes
(id, for_subordinate, for_yourself)
VALUES(4, false, false);

INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(1, 1, 2);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(1, 2, 2);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(1, 3, 2);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(1, 6, 2);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(1, 9, 2);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(1, 12, 2);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(2, 1, 1);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(2, 2, 1);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(2, 3, 1);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(2, 6, 1);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(2, 9, 1);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(2, 12, 1);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(3, 1, 3);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(3, 4, 3);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(3, 5, 3);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(3, 7, 3);
INSERT INTO roles_permissions
(role_id, permission_id, scope_id)
VALUES(3, 8, 3);





