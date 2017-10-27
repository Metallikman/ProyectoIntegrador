<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	$conn=new conectionSQL();
	$conn->startConection();

	$sql = "SELECT de_dependencia as dependencia, de_id FROM dependencias";
	$result=$conn->select($sql);
	$outp = array();
	$outp = $result->fetch_all(MYSQLI_ASSOC);

	echo json_encode($outp);
	$conn->closeConection();		
	
?>