package scalag.serialization

import java.io.{ObjectInputStream, ByteArrayInputStream, ObjectOutputStream, ByteArrayOutputStream}
import org.jboss.marshalling.{MarshallingConfiguration, Marshalling}
import org.jboss.marshalling.river.RiverMarshallerFactory

/**
 * @author Galder Zamarreño
 */
object FunctionSerialization extends App {

  {
    val marshaller = new RiverMarshallerFactory().createMarshaller(new MarshallingConfiguration)
    val baos = new ByteArrayOutputStream
    marshaller.start(Marshalling.createByteOutput(baos))
    marshaller.writeObject(() => "me")
    marshaller.finish()

    println(baos.toByteArray.length)

    val unmarshaller = new RiverMarshallerFactory().createUnmarshaller(new MarshallingConfiguration)
    unmarshaller.start(Marshalling.createByteInput(new ByteArrayInputStream(baos.toByteArray)))
    val function = unmarshaller.readObject().asInstanceOf[() => String]
    unmarshaller.finish()
    println(function.apply())
  }

  {
    val baos = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(() => "me")
    oos.close()

    println(baos.toByteArray.length)

    val bais = new ByteArrayInputStream(baos.toByteArray)
    val ois = new ObjectInputStream(bais)
    val function = ois.readObject().asInstanceOf[() => String]
    println(function.apply())
  }

  {
    val baos = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(baos)
    oos.writeUTF("me")
    oos.close()

    println(baos.toByteArray.length)

    val bais = new ByteArrayInputStream(baos.toByteArray)
    val ois = new ObjectInputStream(bais)
    println(ois.readUTF())
  }

}
