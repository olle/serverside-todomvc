<?php
/*
 * The MIT License
 *
 * Copyright (c) 2008-2014 Olle Törnström studiomediatech.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author  Olle Törnström olle[at]studiomediatech[dot]com
 * @since   2009-09-24
 */
define('DB_FILE', dirname(__FILE__) . '/db.json');

function initDb() {
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

function loadDb() {
	if (!file_exists(DB_FILE))
		return initDb();
	$file = fopen(DB_FILE, 'r');
	$dbData = fread($file, 4096);
	fclose($file);
	$db = json_decode($dbData, true);
	return $db;
}

function saveDb($db) {
	$file = fopen(DB_FILE, 'w');
	$dbData = json_encode($db);
	fwrite($file, $dbData, strlen($dbData));
	fclose($file);
}
