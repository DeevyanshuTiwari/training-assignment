from fastapi import FastAPI

from app.db.database import engine
from app.models.user import User

from app.routes.auth import router as auth_router

User.metadata.create_all(bind=engine)

app = FastAPI()

app.include_router(auth_router)


@app.get("/")
def home():
    return {
        "message": "CircleUp Backend Running"
    }