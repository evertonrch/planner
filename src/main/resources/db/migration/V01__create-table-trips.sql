CREATE TABLE tb_tips (
    id UUID DEFAULT gen_random_uuid(),
    destination VARCHAR(150) NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    owner_name VARCHAR(150) NOT NULL,
    owner_email VARCHAR(150) NOT NULL,
    PRIMARY KEY(id)
);