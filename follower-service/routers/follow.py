from fastapi import APIRouter, Header, HTTPException

from database import get_session

router = APIRouter()


@router.post("/follow/{username}")
def follow_user(username: str, x_username: str = Header(...)):
    if x_username == username:
        raise HTTPException(status_code=400, detail="Ne možeš pratiti samog sebe.")

    with get_session() as session:
        session.run(
            """
            MERGE (me:User {username: $me})
            MERGE (other:User {username: $other})
            MERGE (me)-[:FOLLOWS]->(other)
            """,
            me=x_username, other=username
        )
    return {"message": f"Sada pratiš korisnika {username}."}


@router.delete("/follow/{username}")
def unfollow_user(username: str, x_username: str = Header(...)):
    with get_session() as session:
        session.run(
            """
            MATCH (me:User {username: $me})-[r:FOLLOWS]->(other:User {username: $other})
            DELETE r
            """,
            me=x_username, other=username
        )
    return {"message": f"Prestao si pratiti korisnika {username}."}


@router.get("/following")
def get_following(x_username: str = Header(...)):
    with get_session() as session:
        result = session.run(
            """
            MATCH (me:User {username: $me})-[:FOLLOWS]->(other:User)
            RETURN other.username AS username
            """,
            me=x_username
        )
        return [record["username"] for record in result]


@router.get("/follow/check/{username}")
def check_following(username: str, x_username: str = Header(...)):
    with get_session() as session:
        result = session.run(
            """
            RETURN EXISTS {
                MATCH (me:User {username: $me})-[:FOLLOWS]->(other:User {username: $other})
            } AS is_following
            """,
            me=x_username, other=username
        )
        record = result.single()
        return {"isFollowing": record["is_following"]}
