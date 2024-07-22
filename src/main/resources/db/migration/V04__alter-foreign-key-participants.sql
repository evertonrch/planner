BEGIN;
ALTER TABLE tb_participants DROP CONSTRAINT tb_participants_trip_id_fkey;
ALTER TABLE tb_participants ADD CONSTRAINT tb_participants_trip_id_fkey
    FOREIGN KEY(trip_id)
    REFERENCES tb_trips(id)
    ON DELETE CASCADE NOT VALID;
COMMIT;