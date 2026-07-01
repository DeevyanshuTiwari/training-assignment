from datetime import datetime, timezone

from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.core.auth_dependency import get_current_user
from app.db.dependencies import get_db

from app.models.user import User
from app.models.activity import Activity

from typing import List
from datetime import date
from typing import Optional

from app.schemas.activity import (
    ActivityCreate,
    ActivityUpdate,
    ActivityResponse
)

router = APIRouter(
    prefix="/activities",
    tags=["Activities"]
)


@router.post(
    "",
    response_model=ActivityResponse,
    status_code=201
)
def create_activity(
        activity: ActivityCreate,
        db: Session = Depends(get_db),
        current_user: User = Depends(get_current_user)
):
    activity_datetime = datetime.combine(
        activity.activity_date,
        activity.activity_time
    )

    if activity_datetime <= datetime.now(timezone.utc):
        raise HTTPException(
            status_code=400,
            detail="Activity must be scheduled in the future."
        )

    new_activity = Activity(
        title=activity.title,
        description=activity.description,
        category=activity.category,
        location=activity.location,
        activity_date=activity.activity_date,
        activity_time=activity.activity_time,
        max_participants=activity.max_participants,
        status="OPEN",
        created_by=current_user.id
    )

    db.add(new_activity)
    db.commit()
    db.refresh(new_activity)

    return new_activity


@router.get(
    "",
    response_model=List[ActivityResponse]
)
def get_all_activities(
        db: Session = Depends(get_db)
):
    activities = (
        db.query(Activity)
        .all()
    )

    return activities


@router.get(
    "",
    response_model=list[ActivityResponse]
)
def get_all_activities(
        category: Optional[str] = None,
        location: Optional[str] = None,
        activity_date: Optional[date] = None,
        db: Session = Depends(get_db)
):
    query = db.query(Activity).filter(
        Activity.status == "OPEN"
    )

    if category:
        query = query.filter(
            Activity.category == category
        )

    if location:
        query = query.filter(
            Activity.location == location
        )

    if activity_date:
        query = query.filter(
            Activity.activity_date == activity_date
        )

    return query.all()


@router.put(
    "/{activity_id}",
    response_model=ActivityResponse
)
def update_activity(
        activity_id: int,
        activity_update: ActivityUpdate,
        db: Session = Depends(get_db),
        current_user: User = Depends(get_current_user)
):
    activity = (
        db.query(Activity)
        .filter(Activity.id == activity_id)
        .first()
    )

    if activity is None:
        raise HTTPException(
            status_code=404,
            detail="Activity not found"
        )

    if activity.created_by != current_user.id:
        raise HTTPException(
            status_code=403,
            detail="You can only edit your own activities."
        )

    if (
            activity_update.activity_date
            and activity_update.activity_time
    ):

        updated_datetime = datetime.combine(
            activity_update.activity_date,
            activity_update.activity_time
        )

        if updated_datetime <= datetime.now():
            raise HTTPException(
                status_code=400,
                detail="Activity must be scheduled in the future."
            )

    update_data = activity_update.model_dump(
        exclude_unset=True
    )

    for key, value in update_data.items():
        setattr(activity, key, value)

    db.commit()
    db.refresh(activity)

    return activity


@router.put(
    "/{activity_id}/cancel",
    response_model=ActivityResponse
)
def cancel_activity(
        activity_id: int,
        db: Session = Depends(get_db),
        current_user: User = Depends(get_current_user)
):
    activity = (
        db.query(Activity)
        .filter(Activity.id == activity_id)
        .first()
    )

    if activity is None:
        raise HTTPException(
            status_code=404,
            detail="Activity not found"
        )

    if activity.created_by != current_user.id:
        raise HTTPException(
            status_code=403,
            detail="You can only cancel your own activities."
        )

    if activity.status == "CANCELLED":
        raise HTTPException(
            status_code=400,
            detail="Activity is already cancelled."
        )

    activity.status = "CANCELLED"

    db.commit()
    db.refresh(activity)

    return activity
