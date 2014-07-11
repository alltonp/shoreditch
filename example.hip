============================================================
id <= create thing in:x => price:6000

assert thing exists in:y @id
assert thing price in:z @id 6000
=============================================================

run on:env

=============================================================
"create thing" => on:x/action/create/thing POST
=> {price="6000"}
<= {returnValue="12345"}

"assert thing exists in:y @id" => on:y/assert/thing/exists/@id GET
happy <= {failures=[]}
sad <= {failures=["thing with @id does not exist"]}

"assert thing price in:z @id 6000" => on:z/assert/thing/price/id/6000 GET
happy -> {failures=[]}
sad -> {failures=["price of @id expected x but was y"]}

=============================================================

