<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	$conn=new conectionSQL();
	$conn->startConection();

	$sql = "SELECT ro_id, ro_rol FROM roles";
	$result=$conn->select($sql);
	$outp = array();
	$outp = $result->fetch_all(MYSQLI_ASSOC);

	echo json_encode($outp);
	$conn->closeConection();		
	
?>