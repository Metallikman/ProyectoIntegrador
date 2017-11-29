<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");

	if($_SERVER['REQUEST_METHOD']=="POST"){
		$conn=new conectionSQL();
		$conn->startConection();

		date_default_timezone_set('America/Mexico_City');
		$timedate=date('Y-m-d H:i:s');

		$solicitud=$_POST['solicitud'];
		$idUsuario=$_POST['idUsuario'];
		$detalles=$_POST['detalles'];

		if($solicitud!=""&&$detalles!=""){
			$sql="INSERT INTO tickets (ti_fechaAlta, ti_peticion, ti_detalles, ti_solicitante,ti_status) VALUES ('$timedate','$solicitud','$detalles',$idUsuario,3)";
			$conn->insert($sql);
			echo '[{"success":"Ticket generado con éxito."}]';
		}else{
			echo '[{"error":"Favor de completar todos los campos."}]';
		}
		$conn->closeConection();

	}

?>