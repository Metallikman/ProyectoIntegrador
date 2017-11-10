<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_POST){

		$conn=new conectionSQL();
		$conn->startConection();
		
		if(isset($_POST['nombre']) && isset($_POST['apellido']) && isset($_POST['correo']) && isset($_POST['pass']) && isset($_POST['dependencia'])){
			if (filter_var($_POST['correo'], FILTER_VALIDATE_EMAIL)&&strpos($_POST['correo'],"@ucol.mx")) {
				$sql="INSERT INTO rebuc.usuarios (us_nombre, us_apellido, us_correo, us_pass, us_dependencia, us_rol, us_status) VALUES ('".$_POST['nombre']."','".$_POST['apellido']."','".$_POST['correo']."','".$_POST['pass']."',".$_POST['dependencia'].",2,1)";
				$conn->insert($sql);
				echo '[{"success":"Usuario agregado."}]';
			}else{
				echo '[{"error":"Introduzca un correo válido."}]';
			}
		}else{
			echo '[{"error":"Favor de completar todos los campos."}]';
		}
	}
	
?>