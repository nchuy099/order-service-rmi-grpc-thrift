from locust import User, task

class DummyUser(User):
    @task
    def dummy(self):
        pass
