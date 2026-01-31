package lib;

import lib.file.File;
import lib.http.Http;
import lib.input.Input;
import lib.print.Print;
import lib.sqrt.Sqrt;
import parser.Block;

public class Core extends Block {
    public Core() {
        super(
            new Print(),
            new Input(),
            new Sqrt(),
            new File(),
            new Http()
        );
    }
}
