insert into workspace (id, name, url, created_at, updated_at, owner_id)
values (1, 'Sleact', 'sleact', NOW(), NOW(), 1);

insert into channel (id, name, private, created_at, updated_at, workspace_id)
values (2, '일반', 0, NOW(), NOW(), 1);

update hibernate_sequence
set next_val = 3;