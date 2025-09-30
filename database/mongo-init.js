db = db.getSiblingDB('admin');
db.createUser({
  user: 'Grant',
  pwd: 'testpassword123',
  roles: [{ role: 'root', db: 'admin' }]
});

db = db.getSiblingDB('keplara');
db.createCollection('keplara');
db.keplara.insertOne({ hello: "world" })
db.keplara.insertOne({ message: "This is a test document" });
db.keplara.insertOne({ status: "initialized" });
db.keplara.insertOne({ version: "1.0.0" });
db.keplara.insertOne({ timestamp: new Date() });
