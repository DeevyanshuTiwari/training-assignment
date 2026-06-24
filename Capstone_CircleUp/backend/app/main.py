from fastapi import FastAPI

from app.db.database import engine
from app.models.user import User

User.metadata.create_all(bind=engine)

app = FastAPI()

@app.get("/")
def home():
    return {
        "message": "CircleUp Backend Running"
    }