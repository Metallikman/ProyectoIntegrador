<?php
	include ("controladorConexionMySql.php");

	if($_POST){
		$conn=new conectionSQL();
		$conn->startConection();

		date_default_timezone_set('America/Mexico_City');
		$timedate=date('Y-m-d H:i:s');

		$solicitud=$_POST['solicitud'];
		$idUsuario=$_POST['idUsuario'];

		if($solicitud!=""){
			$sql="INSERT INTO tickets (ti_fechaAlta, ti_peticion, ti_solicitante,ti_status) VALUES ('".$timedate."','".$solicitud."',".$idUsuario.",3)";
			$conn->insert($sql);
			echo '[{"success":"Ticket generado con éxito."}]';
		}else{
			echo '[{"error":"Favor de completar todos los campos."}]';
		}
		$conn->closeConection();

	}

?>