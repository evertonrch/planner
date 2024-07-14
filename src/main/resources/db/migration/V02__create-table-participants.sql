CREATE TABLE tb_participants (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trip_id UUID,

    PRIMARY KEY(id),
    FOREIGN KEY(trip_id) REFERENCES tb_trips (id)
);