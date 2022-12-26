<?php

define('DB_FILE', dirname(__FILE__) . '/db.json');

function initDb()
{
	$db = array(
		'meta' => array(),
		'items' => array()
	);
	$dbData = json_encode($db);
	$file = fopen(DB_FILE, 'w');
	fwrite($file, $dbData, strlen($dbData));
	fclose($file);
	return $db;
}

function loadDb()
{
	if (!file_exists(DB_FILE))
		return initDb();
	$file = fopen(DB_FILE, 'r');
	$dbData = fread($file, 4096);
	fclose($file);
	$db = json_decode($dbData, true);
	return $db;
}

function saveDb($db)
{
	$file = fopen(DB_FILE, 'w');
	$dbData = json_encode($db);
	fwrite($file, $dbData, strlen($dbData));
	fclose($file);
}