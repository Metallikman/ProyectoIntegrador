<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");

	if($_SERVER['REQUEST_METHOD']=="POST"){
		$conn=new conectionSQL();
		$conn->startConection();

		if (isset($_POST['fechaInicio'])&&isset($_POST['fechaFin'])&&isset($_POST['dependencia'])&&isset($_POST['bibliotecario'])&&isset($_POST['solicitante'])) {
			$sql="SELECT *  FROM id3295737_rebuc.vw_tickets_completos WHERE (fechaAlta BETWEEN '".$_POST['fechaInicio']."' AND '".$_POST['fechaFin']."') AND dependenciaSolicitante=".$_POST['dependencia']." AND idBibliotecario=".$_POST['bibliotecario']." AND idSolicitante = ".$_POST['solicitante']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['fechaInicio'])&&isset($_POST['fechaFin'])&&isset($_POST['dependencia'])&&isset($_POST['bibliotecario'])){					
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE (fechaAlta BETWEEN '".$_POST['fechaInicio']."' AND '".$_POST['fechaFin']."') AND dependenciaSolicitante=".$_POST['dependencia']." AND idBibliotecario=".$_POST['bibliotecario']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
			
		}else if(isset($_POST['dependencia'])&&isset($_POST['bibliotecario'])&&isset($_POST['solicitante'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE  dependenciaSolicitante=".$_POST['dependencia']." AND idBibliotecario=".$_POST['bibliotecario']." AND idSolicitante = ".$_POST['solicitante']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if (isset($_POST['fechaInicio'])&&isset($_POST['fechaFin'])&&isset($_POST['dependencia'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE (fechaAlta BETWEEN '".$_POST['fechaInicio']."' AND '".$_POST['fechaFin']."') AND dependenciaSolicitante=".$_POST['dependencia']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
					
		}else if(isset($_POST['dependencia'])&&isset($_POST['bibliotecario'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE  dependenciaSolicitante=".$_POST['dependencia']." AND idBibliotecario=".$_POST['bibliotecario']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['dependencia'])&&isset($_POST['solicitante'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE  dependenciaSolicitante=".$_POST['dependencia']." AND idSolicitante=".$_POST['solicitante']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['fechaInicio'])&&isset($_POST['fechaFin'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE fechaAlta BETWEEN '".$_POST['fechaInicio']."' AND '".$_POST['fechaFin']."' ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);	

		}else if(isset($_POST['dependencia'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE  dependenciaSolicitante=".$_POST['dependencia']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['bibliotecario'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE  idBibliotecario=".$_POST['bibliotecario']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else if(isset($_POST['solicitante'])){
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos WHERE  idSolicitante=".$_POST['solicitante']." ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);

		}else{
			$sql="SELECT * FROM id3295737_rebuc.vw_tickets_completos ORDER BY fechaAlta DESC";
			$res=$conn->select($sql);
			$outp = array();
			$outp = $res->fetch_all(MYSQLI_ASSOC);
			echo json_encode($outp);
		}

		$conn->closeConection();

	}

?>