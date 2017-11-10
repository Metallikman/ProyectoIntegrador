<?php
	include ("controladorConexionMySql.php");

	if($_POST){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idUsuario'])){					
			$sql="SELECT * FROM rebuc.vw_tickets WHERE idSolicitante=".$_POST['idUsuario']." ORDER BY fechaAlta DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
			
		}else if (isset($_POST['idDependencia'])){
			$sql="SELECT * FROM rebuc.vw_tickets WHERE idDependencia=".$_POST['idDependencia']." ORDER BY fechaAlta DESC;";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
					
		}else if(isset($_POST['getAllReports'])){
			$sql="SELECT * FROM rebuc.vw_tickets ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);	

		}else if (isset($_POST['busqueda'])) {
			$sql="SELECT * FROM rebuc.vw_tickets WHERE peticion LIKE '%".$_POST['busqueda']."%'ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
		}

		$conn->closeConection();

	}

?>