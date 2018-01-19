
INSERT INTO hibernate_sequences (sequence_name,sequence_next_hi_value)
VALUES ('app', (select max(app_id)+1 from app));