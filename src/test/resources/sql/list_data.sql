-- Insert test data for boards
INSERT INTO boards (id, title, board_key) VALUES
(1, 'Project Board', 'projectBoard123');

-- Insert test data for lists associated with boards
INSERT INTO lists (id, title, board_id) VALUES
(1, 'Backlog', 1),
(2, 'In Progress', 1);
