build:
	@cd backend && \
	sudo docker image build -t truongvanhuy2000/appnoithatbackend . && \
	sudo docker push truongvanhuy2000/appnoithatbackend
stop:
	@sudo docker compose stop
start:
	@sudo docker pull truongvanhuy2000/appnoithatbackend && \
	sudo docker compose up -d