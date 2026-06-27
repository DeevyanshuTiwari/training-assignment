from datetime import datetime

from pydantic import BaseModel


class ParticipationCreate(BaseModel):
    pass


class ParticipationResponse(BaseModel):
    id: int

    activity_id: int

    user_id: int

    status: str

    requested_at: datetime

    class Config:
        from_attributes = True


class ParticipationUpdate(BaseModel):
    status: str
