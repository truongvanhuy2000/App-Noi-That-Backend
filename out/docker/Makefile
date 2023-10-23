build:
	@cd backend && \
	sudo docker image build -t truongvanhuy2000/appnoithatbackend . && \
	sudo docker push truongvanhuy2000/appnoithatbackend
run:
	@sudo docker pull truongvanhuy2000/appnoithatbackend && \
	sudo docker compose up -d db && \
	sleep 10 && \
	sudo docker compose up -d backend
stop:
	@sudo docker compose stop
