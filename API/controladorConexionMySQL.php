<?php

	class conectionSQL
	{
		private $server = "localhost";
		private $username = "root";
		private $pass = "";
		private $dbname = "rebuc";
		private $conn = "";
		private $error = "";
		/**
		*Creando conexión
		**/
		function startConection(){
			$this->conn = new mysqli($this->server, $this->username, $this->pass, $this->dbname);
			$this->conn->set_charset("utf8");
		}

		function closeConection(){
			$this->conn->close();
		}

		function insert($sql){
			$this->conn->query($sql);
		}

		function select($sql){
			return $this->conn->query($sql);
		}

		function update($sql){
			$this->conn->query($sql);
		}

		function delete($sql){
			$this->conn->query($sql);
		}

		function obtenerUltimoId(){
			return $this->conn->insert_id;
		}

		function setError(){
			$this->error=$this->conn->connect_error;
		}
		function getError(){
			return $this->error;
		}

	}
?>