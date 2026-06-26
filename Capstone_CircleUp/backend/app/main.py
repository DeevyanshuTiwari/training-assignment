from fastapi import FastAPI

from app.db.database import engine, Base

from app.routes.auth import router as auth_router
from app.routes.user import router as user_router
from app.routes.activity import router as activity_router

Base.metadata.create_all(bind=engine)

app = FastAPI()

app.include_router(auth_router)
app.include_router(user_router)
app.include_router(activity_router)


@app.get("/")
def home():
    return {
        "message": "CircleUp Backend Running"
    }
