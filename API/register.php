<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	//header('Content-Type: text/html; charset=UTF-8');
	
	if($_POST){

		$conn=new conectionSQL();
		$conn->startConection();

		$fname=$_POST['fname'];
		$lname=$_POST['lname'];
		$email=$_POST['email'];
		$pass=$_POST['pass'];
		$dependencia=$_POST['dependencia'];

		
		if($fname!=""&&$lname!=""&&$email!=""&&$pass!=""&&$dependencia!=""){
			if (filter_var($email, FILTER_VALIDATE_EMAIL)&&strpos($email,"@ucol.mx")) {
				//if($checkBox=='true'){
					$sqlQuery="SELECT us_correo FROM usuarios WHERE us_correo='".$email."'";
					$res=$conn->select($sqlQuery);				
					if(!mysqli_num_rows($res)>0){		
						//if ($pass==$checkPass) {
							$sql="INSERT INTO usuarios (us_nombre,us_apellido,us_correo,
							us_pass,us_dependencia,us_status,us_rol) VALUES ('".$fname."','".$lname."','".$email."','".$pass."',1,2,3)";
							$conn->insert($sql);
							echo '[{"success":"Usuario registrado correctamente."}]';
							
								
						//}else{
						//	echo "Las contraseñas no coinciden.";
						//}
					}else{
						echo '[{"error":"Este correo ya ha sido registrado."}]';
					}
				//}else{
					//echo "No ha aceptado las condiciones de uso.";
				//}
	  		}else{
	  			echo '[{"error":"Introduzca un correo válido."}]';
	  		}
		}else{
			echo '[{"error":"Favor de completar todos los campos."}]';
		}
				
		$conn->closeConection();

	}
	
?>