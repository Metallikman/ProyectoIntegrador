<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");

	$conn=new conectionSQL();
	$conn->startConection();

	if($_SERVER['REQUEST_METHOD']=="POST"){
		$email=$_POST['email'];
		$pass=$_POST['pass'];


		if($email!=""&&$pass!=""){
			if(filter_var($email, FILTER_VALIDATE_EMAIL)){

				$sql="SELECT us_correo, us_pass, us_status FROM usuarios WHERE us_correo='".$email."'";
				$res=$conn->select($sql);

				if(mysqli_num_rows($res)==1){				
					$row = mysqli_fetch_assoc($res);
					if($row['us_status']==6){
						echo "Su cuenta ha sido eliminada por un administrador.";
					}else if($row['us_status']==2){
						echo "Su cuenta no está aprovada aún.";;
					}else{
						if($email==$row['us_correo'] && $pass==$row['us_pass']){
	   						
	   						$sql="SELECT us_correo, us_rol, us_id, us_dependencia, concat(us_nombre,' ',us_apellido) AS nombreCompleto FROM usuarios WHERE us_correo='".$email."'";
							$res=$conn->select($sql);
	   						$outp = array();
							$outp = $res->fetch_all(MYSQLI_ASSOC);
	   						
	   						echo json_encode($outp);
						}else {
							echo '[{"error":"Correo o contraseña incorrecta."}]';
						}
					}
				}else{
					echo '[{"error":"Este correo no está registrado."}]';
				}
			}else{
				echo '[{"error":"El correo introducido no es valido."}]';
			}
		}else{
			echo '[{"error":"Por favor, complete los campos."}]';
		}
		
		$conn->closeConection();
	}
	
?>