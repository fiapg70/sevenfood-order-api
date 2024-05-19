SELECT 'CREATE DATABASE "sevenfood-order-db"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'sevenfood-order-db')\gexec
