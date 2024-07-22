CREATE TABLE tb_activities (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trip_id UUID,
    FOREIGN KEY(trip_id)
        REFERENCES tb_trips(id)
        ON DELETE CASCADE
);