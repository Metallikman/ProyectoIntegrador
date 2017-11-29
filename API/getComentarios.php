<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	//header('Content-Type: text/html; charset=UTF-8');
	
	if($_SERVER['REQUEST_METHOD']=="POST"){

		$conn=new conectionSQL();
		$conn->startConection();

		$idTicket=$_POST['idTicket'];

		if($idTicket!=""){
			$sql="SELECT * FROM id3295737_rebuc.vw_comentarios WHERE idTicket=".$idTicket." ORDER BY fecha DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
		}else{
			echo '[{"error":"Id de ticket no ingresado."}]';
		}
		
		$conn->closeConection();

	}
	
?>