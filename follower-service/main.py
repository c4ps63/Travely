from fastapi import FastAPI
from routers import follow

app = FastAPI()

app.include_router(follow.router)

@app.get("/health")
def health():
    return {"status": "ok"}
