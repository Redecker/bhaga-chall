all: BhagaChallInterface.class BhagaChall.class BhagaChallImpl.class \
     BhagaChallClient.class BhagaChallServer.class			  

BhagaChallInterface.class: BhagaChallInterface.java
			@javac BhagaChallInterface.java

BhagaChall.class: BhagaChall.java
		  @javac BhagaChall.java

BhagaChallImpl.class: BhagaChallImpl.java BhagaChallInterface.class
			@javac BhagaChallImpl.java

BhagaChallServer.class:	BhagaChallServer.java
			@javac BhagaChallServer.java

BhagaChallClient.class:	BhagaChallClient.java
			@javac BhagaChallClient.java

run:			all
			@java BhagaChallServer &
			@sleep 1
			@java BhagaChallClient

clean:
			@rm -f *.class *~

info:
			@echo "(c) Roland Teodorowitsch (08 abr. 2015)"

