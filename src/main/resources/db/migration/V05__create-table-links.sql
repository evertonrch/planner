CREATE TABLE tb_links (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    url VARCHAR(200) NOT NULL,
    trip_id UUID,
    FOREIGN KEY(trip_id)
        REFERENCES tb_trips(id)
        ON DELETE CASCADE
);